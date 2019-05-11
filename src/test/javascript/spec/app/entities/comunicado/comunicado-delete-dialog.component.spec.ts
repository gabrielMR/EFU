/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EfuTestModule } from '../../../test.module';
import { ComunicadoDeleteDialogComponent } from 'app/entities/comunicado/comunicado-delete-dialog.component';
import { ComunicadoService } from 'app/entities/comunicado/comunicado.service';

describe('Component Tests', () => {
  describe('Comunicado Management Delete Component', () => {
    let comp: ComunicadoDeleteDialogComponent;
    let fixture: ComponentFixture<ComunicadoDeleteDialogComponent>;
    let service: ComunicadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ComunicadoDeleteDialogComponent]
      })
        .overrideTemplate(ComunicadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComunicadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunicadoService);
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
