/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { RecorridoUpdateComponent } from 'app/entities/recorrido/recorrido-update.component';
import { RecorridoService } from 'app/entities/recorrido/recorrido.service';
import { Recorrido } from 'app/shared/model/recorrido.model';

describe('Component Tests', () => {
  describe('Recorrido Management Update Component', () => {
    let comp: RecorridoUpdateComponent;
    let fixture: ComponentFixture<RecorridoUpdateComponent>;
    let service: RecorridoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [RecorridoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RecorridoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecorridoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecorridoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Recorrido(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Recorrido();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
