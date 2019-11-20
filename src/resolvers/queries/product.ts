import { Context } from '../../utils';
import { Product } from '../../generated/prisma-client';

const products = async (_parent, _args, ctx: Context): Promise<Product[]> => {
  return await ctx.prisma.products({ where: { deleted: false } });
};

export default {
  products,
};
