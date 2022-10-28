import { Product } from '@shared/services/product/product';

export const fakeProducts: Product[] = [
  {
    code: 'LIVRE',
    name: 'livres',
    imported: false,
    withoutTaxesPrice: 12.49,
    type: 'LIVRE',
  },
  {
    code: 'CDMUS',
    name: 'CD musicaux',
    imported: false,
    withoutTaxesPrice: 14.99,
    type: 'AUTRE',
  },
  {
    code: 'BOCHO1-IMP',
    name: 'boites de chocolat',
    imported: true,
    withoutTaxesPrice: 10,
    type: 'PREMIERE_NECESSITE',
  },
  {
    code: 'BOCHO2-IMP',
    name: 'boites de chocolat',
    imported: true,
    withoutTaxesPrice: 11.25,
    type: 'PREMIERE_NECESSITE',
  },
  {
    code: 'BACHO',
    name: 'barres de chocolat',
    imported: false,
    withoutTaxesPrice: 0.85,
    type: 'PREMIERE_NECESSITE',
  },
  {
    code: 'PARFU1-IMP',
    name: 'Flacons de partum',
    imported: true,
    withoutTaxesPrice: 47.5,
    type: 'AUTRE',
  },
  {
    code: 'PARFU2-IMP',
    name: 'Flacons de partum',
    imported: true,
    withoutTaxesPrice: 27.99,
    type: 'AUTRE',
  },
  {
    code: 'PARFU3',
    name: 'Flacons de partum',
    imported: false,
    withoutTaxesPrice: 18.99,
    type: 'AUTRE',
  },
];
