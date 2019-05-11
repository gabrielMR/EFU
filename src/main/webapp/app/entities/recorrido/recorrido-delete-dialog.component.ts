import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecorrido } from 'app/shared/model/recorrido.model';
import { RecorridoService } from './recorrido.service';

@Component({
  selector: 'jhi-recorrido-delete-dialog',
  templateUrl: './recorrido-delete-dialog.component.html'
})
export class RecorridoDeleteDialogComponent {
  recorrido: IRecorrido;

  constructor(protected recorridoService: RecorridoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recorridoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'recorridoListModification',
        content: 'Deleted an recorrido'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-recorrido-delete-popup',
  template: ''
})
export class RecorridoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recorrido }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RecorridoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.recorrido = recorrido;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/recorrido', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/recorrido', { outlets: { popup: null } }]);
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
