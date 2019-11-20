import customer from './customer';
import order from './order';
import product from './product';

const Query = {
  ...customer,
  ...order,
  ...product,
};

export default Query;
