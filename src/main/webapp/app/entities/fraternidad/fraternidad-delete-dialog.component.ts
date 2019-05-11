import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFraternidad } from 'app/shared/model/fraternidad.model';
import { FraternidadService } from './fraternidad.service';

@Component({
  selector: 'jhi-fraternidad-delete-dialog',
  templateUrl: './fraternidad-delete-dialog.component.html'
})
export class FraternidadDeleteDialogComponent {
  fraternidad: IFraternidad;

  constructor(
    protected fraternidadService: FraternidadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fraternidadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fraternidadListModification',
        content: 'Deleted an fraternidad'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fraternidad-delete-popup',
  template: ''
})
export class FraternidadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fraternidad }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FraternidadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fraternidad = fraternidad;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fraternidad', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fraternidad', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
