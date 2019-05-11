import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDelegado } from 'app/shared/model/delegado.model';
import { DelegadoService } from './delegado.service';

@Component({
  selector: 'jhi-delegado-delete-dialog',
  templateUrl: './delegado-delete-dialog.component.html'
})
export class DelegadoDeleteDialogComponent {
  delegado: IDelegado;

  constructor(protected delegadoService: DelegadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.delegadoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'delegadoListModification',
        content: 'Deleted an delegado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-delegado-delete-popup',
  template: ''
})
export class DelegadoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ delegado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DelegadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.delegado = delegado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/delegado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/delegado', { outlets: { popup: null } }]);
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
