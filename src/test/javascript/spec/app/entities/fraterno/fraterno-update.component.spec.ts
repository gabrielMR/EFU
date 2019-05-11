/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { FraternoUpdateComponent } from 'app/entities/fraterno/fraterno-update.component';
import { FraternoService } from 'app/entities/fraterno/fraterno.service';
import { Fraterno } from 'app/shared/model/fraterno.model';

describe('Component Tests', () => {
  describe('Fraterno Management Update Component', () => {
    let comp: FraternoUpdateComponent;
    let fixture: ComponentFixture<FraternoUpdateComponent>;
    let service: FraternoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FraternoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FraternoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraternoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fraterno(123);
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
        const entity = new Fraterno();
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
