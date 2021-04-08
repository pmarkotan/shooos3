import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBanner, Banner } from '../banner.model';

import { BannerService } from './banner.service';

describe('Service Tests', () => {
  describe('Banner Service', () => {
    let service: BannerService;
    let httpMock: HttpTestingController;
    let elemDefault: IBanner;
    let expectedResult: IBanner | IBanner[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BannerService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        img: 'AAAAAAA',
        url: 'AAAAAAA',
        title: 'AAAAAAA',
        active: 'AAAAAAA',
        position: 'AAAAAAA',
        store: 'AAAAAAA',
        type: 'AAAAAAA',
        html: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Banner', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Banner()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Banner', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            img: 'BBBBBB',
            url: 'BBBBBB',
            title: 'BBBBBB',
            active: 'BBBBBB',
            position: 'BBBBBB',
            store: 'BBBBBB',
            type: 'BBBBBB',
            html: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Banner', () => {
        const patchObject = Object.assign(
          {
            position: 'BBBBBB',
          },
          new Banner()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Banner', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            img: 'BBBBBB',
            url: 'BBBBBB',
            title: 'BBBBBB',
            active: 'BBBBBB',
            position: 'BBBBBB',
            store: 'BBBBBB',
            type: 'BBBBBB',
            html: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Banner', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBannerToCollectionIfMissing', () => {
        it('should add a Banner to an empty array', () => {
          const banner: IBanner = { id: 123 };
          expectedResult = service.addBannerToCollectionIfMissing([], banner);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(banner);
        });

        it('should not add a Banner to an array that contains it', () => {
          const banner: IBanner = { id: 123 };
          const bannerCollection: IBanner[] = [
            {
              ...banner,
            },
            { id: 456 },
          ];
          expectedResult = service.addBannerToCollectionIfMissing(bannerCollection, banner);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Banner to an array that doesn't contain it", () => {
          const banner: IBanner = { id: 123 };
          const bannerCollection: IBanner[] = [{ id: 456 }];
          expectedResult = service.addBannerToCollectionIfMissing(bannerCollection, banner);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(banner);
        });

        it('should add only unique Banner to an array', () => {
          const bannerArray: IBanner[] = [{ id: 123 }, { id: 456 }, { id: 81311 }];
          const bannerCollection: IBanner[] = [{ id: 123 }];
          expectedResult = service.addBannerToCollectionIfMissing(bannerCollection, ...bannerArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const banner: IBanner = { id: 123 };
          const banner2: IBanner = { id: 456 };
          expectedResult = service.addBannerToCollectionIfMissing([], banner, banner2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(banner);
          expect(expectedResult).toContain(banner2);
        });

        it('should accept null and undefined values', () => {
          const banner: IBanner = { id: 123 };
          expectedResult = service.addBannerToCollectionIfMissing([], null, banner, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(banner);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
