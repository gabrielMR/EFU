/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EfuTestModule } from '../../../test.module';
import { DelegadoDeleteDialogComponent } from 'app/entities/delegado/delegado-delete-dialog.component';
import { DelegadoService } from 'app/entities/delegado/delegado.service';

describe('Component Tests', () => {
  describe('Delegado Management Delete Component', () => {
    let comp: DelegadoDeleteDialogComponent;
    let fixture: ComponentFixture<DelegadoDeleteDialogComponent>;
    let service: DelegadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [DelegadoDeleteDialogComponent]
      })
        .overrideTemplate(DelegadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DelegadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegadoService);
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
