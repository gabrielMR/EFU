/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EfuTestModule } from '../../../test.module';
import { PremioDeleteDialogComponent } from 'app/entities/premio/premio-delete-dialog.component';
import { PremioService } from 'app/entities/premio/premio.service';

describe('Component Tests', () => {
  describe('Premio Management Delete Component', () => {
    let comp: PremioDeleteDialogComponent;
    let fixture: ComponentFixture<PremioDeleteDialogComponent>;
    let service: PremioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [PremioDeleteDialogComponent]
      })
        .overrideTemplate(PremioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PremioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PremioService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
