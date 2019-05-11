import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComunicado } from 'app/shared/model/comunicado.model';
import { ComunicadoService } from './comunicado.service';

@Component({
  selector: 'jhi-comunicado-delete-dialog',
  templateUrl: './comunicado-delete-dialog.component.html'
})
export class ComunicadoDeleteDialogComponent {
  comunicado: IComunicado;

  constructor(
    protected comunicadoService: ComunicadoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comunicadoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comunicadoListModification',
        content: 'Deleted an comunicado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-comunicado-delete-popup',
  template: ''
})
export class ComunicadoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comunicado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComunicadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.comunicado = comunicado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/comunicado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/comunicado', { outlets: { popup: null } }]);
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
