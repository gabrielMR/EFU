import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INiusta } from 'app/shared/model/niusta.model';
import { NiustaService } from './niusta.service';

@Component({
  selector: 'jhi-niusta-delete-dialog',
  templateUrl: './niusta-delete-dialog.component.html'
})
export class NiustaDeleteDialogComponent {
  niusta: INiusta;

  constructor(protected niustaService: NiustaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.niustaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'niustaListModification',
        content: 'Deleted an niusta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-niusta-delete-popup',
  template: ''
})
export class NiustaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ niusta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NiustaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.niusta = niusta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/niusta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/niusta', { outlets: { popup: null } }]);
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
