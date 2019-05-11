/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EfuTestModule } from '../../../test.module';
import { FraternidadDeleteDialogComponent } from 'app/entities/fraternidad/fraternidad-delete-dialog.component';
import { FraternidadService } from 'app/entities/fraternidad/fraternidad.service';

describe('Component Tests', () => {
  describe('Fraternidad Management Delete Component', () => {
    let comp: FraternidadDeleteDialogComponent;
    let fixture: ComponentFixture<FraternidadDeleteDialogComponent>;
    let service: FraternidadService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternidadDeleteDialogComponent]
      })
        .overrideTemplate(FraternidadDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FraternidadDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraternidadService);
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
