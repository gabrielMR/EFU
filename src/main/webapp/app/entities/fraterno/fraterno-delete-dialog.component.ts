import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFraterno } from 'app/shared/model/fraterno.model';
import { FraternoService } from './fraterno.service';

@Component({
  selector: 'jhi-fraterno-delete-dialog',
  templateUrl: './fraterno-delete-dialog.component.html'
})
export class FraternoDeleteDialogComponent {
  fraterno: IFraterno;

  constructor(protected fraternoService: FraternoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fraternoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fraternoListModification',
        content: 'Deleted an fraterno'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fraterno-delete-popup',
  template: ''
})
export class FraternoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fraterno }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FraternoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fraterno = fraterno;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fraterno', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fraterno', { outlets: { popup: null } }]);
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
