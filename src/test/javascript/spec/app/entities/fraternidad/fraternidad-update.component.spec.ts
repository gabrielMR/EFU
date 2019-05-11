/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { FraternidadUpdateComponent } from 'app/entities/fraternidad/fraternidad-update.component';
import { FraternidadService } from 'app/entities/fraternidad/fraternidad.service';
import { Fraternidad } from 'app/shared/model/fraternidad.model';

describe('Component Tests', () => {
  describe('Fraternidad Management Update Component', () => {
    let comp: FraternidadUpdateComponent;
    let fixture: ComponentFixture<FraternidadUpdateComponent>;
    let service: FraternidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FraternidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FraternidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraternidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fraternidad(123);
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
        const entity = new Fraternidad();
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
