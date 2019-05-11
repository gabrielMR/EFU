/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EfuTestModule } from '../../../test.module';
import { RecorridoDeleteDialogComponent } from 'app/entities/recorrido/recorrido-delete-dialog.component';
import { RecorridoService } from 'app/entities/recorrido/recorrido.service';

describe('Component Tests', () => {
  describe('Recorrido Management Delete Component', () => {
    let comp: RecorridoDeleteDialogComponent;
    let fixture: ComponentFixture<RecorridoDeleteDialogComponent>;
    let service: RecorridoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [RecorridoDeleteDialogComponent]
      })
        .overrideTemplate(RecorridoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecorridoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecorridoService);
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
