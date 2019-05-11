/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EfuTestModule } from '../../../test.module';
import { DelegadoDetailComponent } from 'app/entities/delegado/delegado-detail.component';
import { Delegado } from 'app/shared/model/delegado.model';

describe('Component Tests', () => {
  describe('Delegado Management Detail Component', () => {
    let comp: DelegadoDetailComponent;
    let fixture: ComponentFixture<DelegadoDetailComponent>;
    const route = ({ data: of({ delegado: new Delegado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EfuTestModule],
        declarations: [DelegadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DelegadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DelegadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.delegado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
