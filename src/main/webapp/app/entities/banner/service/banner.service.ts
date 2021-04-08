import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBanner, getBannerIdentifier } from '../banner.model';

export type EntityResponseType = HttpResponse<IBanner>;
export type EntityArrayResponseType = HttpResponse<IBanner[]>;

@Injectable({ providedIn: 'root' })
export class BannerService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/banners');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(banner: IBanner): Observable<EntityResponseType> {
    return this.http.post<IBanner>(this.resourceUrl, banner, { observe: 'response' });
  }

  update(banner: IBanner): Observable<EntityResponseType> {
    return this.http.put<IBanner>(`${this.resourceUrl}/${getBannerIdentifier(banner) as number}`, banner, { observe: 'response' });
  }

  partialUpdate(banner: IBanner): Observable<EntityResponseType> {
    return this.http.patch<IBanner>(`${this.resourceUrl}/${getBannerIdentifier(banner) as number}`, banner, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBanner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBanner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBannerToCollectionIfMissing(bannerCollection: IBanner[], ...bannersToCheck: (IBanner | null | undefined)[]): IBanner[] {
    const banners: IBanner[] = bannersToCheck.filter(isPresent);
    if (banners.length > 0) {
      const bannerCollectionIdentifiers = bannerCollection.map(bannerItem => getBannerIdentifier(bannerItem)!);
      const bannersToAdd = banners.filter(bannerItem => {
        const bannerIdentifier = getBannerIdentifier(bannerItem);
        if (bannerIdentifier == null || bannerCollectionIdentifiers.includes(bannerIdentifier)) {
          return false;
        }
        bannerCollectionIdentifiers.push(bannerIdentifier);
        return true;
      });
      return [...bannersToAdd, ...bannerCollection];
    }
    return bannerCollection;
  }
}
