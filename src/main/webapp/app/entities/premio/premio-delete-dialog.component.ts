import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPremio } from 'app/shared/model/premio.model';
import { PremioService } from './premio.service';

@Component({
  selector: 'jhi-premio-delete-dialog',
  templateUrl: './premio-delete-dialog.component.html'
})
export class PremioDeleteDialogComponent {
  premio: IPremio;

  constructor(protected premioService: PremioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.premioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'premioListModification',
        content: 'Deleted an premio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-premio-delete-popup',
  template: ''
})
export class PremioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ premio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PremioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.premio = premio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/premio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/premio', { outlets: { popup: null } }]);
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
