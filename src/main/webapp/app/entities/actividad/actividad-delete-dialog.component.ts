import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActividad } from 'app/shared/model/actividad.model';
import { ActividadService } from './actividad.service';

@Component({
  selector: 'jhi-actividad-delete-dialog',
  templateUrl: './actividad-delete-dialog.component.html'
})
export class ActividadDeleteDialogComponent {
  actividad: IActividad;

  constructor(protected actividadService: ActividadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.actividadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'actividadListModification',
        content: 'Deleted an actividad'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-actividad-delete-popup',
  template: ''
})
export class ActividadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ actividad }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ActividadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.actividad = actividad;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/actividad', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/actividad', { outlets: { popup: null } }]);
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
