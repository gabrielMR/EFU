/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { ActividadUpdateComponent } from 'app/entities/actividad/actividad-update.component';
import { ActividadService } from 'app/entities/actividad/actividad.service';
import { Actividad } from 'app/shared/model/actividad.model';

describe('Component Tests', () => {
  describe('Actividad Management Update Component', () => {
    let comp: ActividadUpdateComponent;
    let fixture: ComponentFixture<ActividadUpdateComponent>;
    let service: ActividadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ActividadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActividadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActividadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActividadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Actividad(123);
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
        const entity = new Actividad();
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
