import { Context } from '../../utils';
import { Order } from '../../generated/prisma-client';

const Customer = {
  orders: async ({ id }, _args, ctx: Context): Promise<Order[]> => {
    return await ctx.prisma.customer({ id }).orders({ where: { deleted: false } });
  },
};

export default Customer;
