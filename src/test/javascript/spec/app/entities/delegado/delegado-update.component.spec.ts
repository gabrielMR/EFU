/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { DelegadoUpdateComponent } from 'app/entities/delegado/delegado-update.component';
import { DelegadoService } from 'app/entities/delegado/delegado.service';
import { Delegado } from 'app/shared/model/delegado.model';

describe('Component Tests', () => {
  describe('Delegado Management Update Component', () => {
    let comp: DelegadoUpdateComponent;
    let fixture: ComponentFixture<DelegadoUpdateComponent>;
    let service: DelegadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [DelegadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DelegadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DelegadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DelegadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Delegado(123);
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
        const entity = new Delegado();
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
