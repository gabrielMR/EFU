/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { ComunicadoUpdateComponent } from 'app/entities/comunicado/comunicado-update.component';
import { ComunicadoService } from 'app/entities/comunicado/comunicado.service';
import { Comunicado } from 'app/shared/model/comunicado.model';

describe('Component Tests', () => {
  describe('Comunicado Management Update Component', () => {
    let comp: ComunicadoUpdateComponent;
    let fixture: ComponentFixture<ComunicadoUpdateComponent>;
    let service: ComunicadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ComunicadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComunicadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComunicadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunicadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Comunicado(123);
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
        const entity = new Comunicado();
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
