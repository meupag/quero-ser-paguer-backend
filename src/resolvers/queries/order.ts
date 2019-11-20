import { Context, getUserId } from '../../utils';
import { Order } from '../../generated/prisma-client';

const orders = async (_parent, _args, ctx: Context): Promise<Order[]> => {
  const userId = getUserId(ctx);
  const orders: Order[] = await ctx.prisma.orders({
    where: {
      deleted: false,
      customer: {
        id: userId,
      },
    },
  });
  return orders;
};

const order = async (_parent, args, ctx: Context): Promise<Order[]> => {
  if (!args.id) throw Error('The id field is required!');
  const userId = getUserId(ctx);
  const orders: Order[] = await ctx.prisma.orders({
    where: {
      deleted: false,
      id: args.id,
      customer: {
        id: userId,
      },
    },
  });
  return orders;
};

export default {
  orders,
  order,
};
