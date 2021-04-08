import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BannerDetailComponent } from './banner-detail.component';

describe('Component Tests', () => {
  describe('Banner Management Detail Component', () => {
    let comp: BannerDetailComponent;
    let fixture: ComponentFixture<BannerDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BannerDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ banner: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BannerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BannerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load banner on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.banner).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
