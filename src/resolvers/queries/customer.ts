import { Context } from '../../utils';
import { Customer } from '../../generated/prisma-client';

const customers = async (_parent, _args, ctx: Context): Promise<Customer[]> => {
  return await ctx.prisma.customers({ where: { deleted: false } });
};

export default {
  customers,
};
