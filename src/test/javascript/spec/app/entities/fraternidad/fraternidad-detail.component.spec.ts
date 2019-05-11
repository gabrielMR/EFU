/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { FraternidadDetailComponent } from 'app/entities/fraternidad/fraternidad-detail.component';
import { Fraternidad } from 'app/shared/model/fraternidad.model';

describe('Component Tests', () => {
  describe('Fraternidad Management Detail Component', () => {
    let comp: FraternidadDetailComponent;
    let fixture: ComponentFixture<FraternidadDetailComponent>;
    const route = ({ data: of({ fraternidad: new Fraternidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [FraternidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FraternidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FraternidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fraternidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
