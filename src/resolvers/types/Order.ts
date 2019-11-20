import { Context } from '../../utils';
import { Order, Customer } from '../../generated/prisma-client';

const Order = {
  customer: async ({ id }, _args, ctx: Context): Promise<Customer> => {
    return await ctx.prisma.order({ id }).customer();
  },
  orderItens: async ({ id }, _args, ctx: Context): Promise<Order[]> => {
    return await ctx.prisma.order({ id }).orderItens({ where: { deleted: false } });
  },
};

export default Order;
