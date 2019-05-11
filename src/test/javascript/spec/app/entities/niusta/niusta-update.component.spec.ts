/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { NiustaUpdateComponent } from 'app/entities/niusta/niusta-update.component';
import { NiustaService } from 'app/entities/niusta/niusta.service';
import { Niusta } from 'app/shared/model/niusta.model';

describe('Component Tests', () => {
  describe('Niusta Management Update Component', () => {
    let comp: NiustaUpdateComponent;
    let fixture: ComponentFixture<NiustaUpdateComponent>;
    let service: NiustaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [NiustaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NiustaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NiustaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NiustaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Niusta(123);
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
        const entity = new Niusta();
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
