/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { ActividadDetailComponent } from 'app/entities/actividad/actividad-detail.component';
import { Actividad } from 'app/shared/model/actividad.model';

describe('Component Tests', () => {
  describe('Actividad Management Detail Component', () => {
    let comp: ActividadDetailComponent;
    let fixture: ComponentFixture<ActividadDetailComponent>;
    const route = ({ data: of({ actividad: new Actividad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [ActividadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActividadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActividadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.actividad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
