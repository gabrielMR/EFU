/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EfuTestModule } from '../../../test.module';
import { NiustaDeleteDialogComponent } from 'app/entities/niusta/niusta-delete-dialog.component';
import { NiustaService } from 'app/entities/niusta/niusta.service';

describe('Component Tests', () => {
  describe('Niusta Management Delete Component', () => {
    let comp: NiustaDeleteDialogComponent;
    let fixture: ComponentFixture<NiustaDeleteDialogComponent>;
    let service: NiustaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [NiustaDeleteDialogComponent]
      })
        .overrideTemplate(NiustaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NiustaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NiustaService);
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
