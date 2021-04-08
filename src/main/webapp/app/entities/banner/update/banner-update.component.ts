import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBanner, Banner } from '../banner.model';
import { BannerService } from '../service/banner.service';

@Component({
  selector: 'jhi-banner-update',
  templateUrl: './banner-update.component.html',
})
export class BannerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    img: [null, [Validators.required, Validators.maxLength(255)]],
    url: [null, [Validators.required, Validators.maxLength(255)]],
    title: [null, [Validators.required, Validators.maxLength(255)]],
    active: [null, [Validators.required, Validators.maxLength(255)]],
    position: [null, [Validators.required, Validators.maxLength(255)]],
    store: [null, [Validators.required, Validators.maxLength(255)]],
    type: [null, [Validators.required, Validators.maxLength(255)]],
    html: [null, [Validators.maxLength(255)]],
  });

  constructor(protected bannerService: BannerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banner }) => {
      this.updateForm(banner);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const banner = this.createFromForm();
    if (banner.id !== undefined) {
      this.subscribeToSaveResponse(this.bannerService.update(banner));
    } else {
      this.subscribeToSaveResponse(this.bannerService.create(banner));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanner>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(banner: IBanner): void {
    this.editForm.patchValue({
      id: banner.id,
      img: banner.img,
      url: banner.url,
      title: banner.title,
      active: banner.active,
      position: banner.position,
      store: banner.store,
      type: banner.type,
      html: banner.html,
    });
  }

  protected createFromForm(): IBanner {
    return {
      ...new Banner(),
      id: this.editForm.get(['id'])!.value,
      img: this.editForm.get(['img'])!.value,
      url: this.editForm.get(['url'])!.value,
      title: this.editForm.get(['title'])!.value,
      active: this.editForm.get(['active'])!.value,
      position: this.editForm.get(['position'])!.value,
      store: this.editForm.get(['store'])!.value,
      type: this.editForm.get(['type'])!.value,
      html: this.editForm.get(['html'])!.value,
    };
  }
}
