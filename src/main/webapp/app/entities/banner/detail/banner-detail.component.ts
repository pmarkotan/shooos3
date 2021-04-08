import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBanner } from '../banner.model';

@Component({
  selector: 'jhi-banner-detail',
  templateUrl: './banner-detail.component.html',
})
export class BannerDetailComponent implements OnInit {
  banner: IBanner | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banner }) => {
      this.banner = banner;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
