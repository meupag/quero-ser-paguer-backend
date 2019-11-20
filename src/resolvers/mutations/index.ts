import auth from './auth';
import product from './product';
import order from './order';

const Mutation = {
  ...auth,
  ...product,
  ...order,
};

export default Mutation;
