import { Context } from '../../utils';
import { OrderItem, Product } from '../../generated/prisma-client';

const OrderItem = {
  product: async ({ id }, _args, ctx: Context): Promise<Product> => {
    return await ctx.prisma.orderItem({ id }).product();
  },
};

export default OrderItem;
