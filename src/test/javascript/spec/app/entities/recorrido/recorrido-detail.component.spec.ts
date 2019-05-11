/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { RecorridoDetailComponent } from 'app/entities/recorrido/recorrido-detail.component';
import { Recorrido } from 'app/shared/model/recorrido.model';

describe('Component Tests', () => {
  describe('Recorrido Management Detail Component', () => {
    let comp: RecorridoDetailComponent;
    let fixture: ComponentFixture<RecorridoDetailComponent>;
    const route = ({ data: of({ recorrido: new Recorrido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [RecorridoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecorridoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecorridoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recorrido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
