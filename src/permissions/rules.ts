import { rule, or } from 'graphql-shield';
import { getUserId, Context } from '../utils';

export const isManager = rule()(async (_parent, _args, ctx: Context) => {
  const id = getUserId(ctx);

  if (id) return await ctx.prisma.$exists.manager({ id, deleted: false });

  return false;
});

export const isCustomer = rule()(async (_parent, _args, ctx: Context) => {
  const id = getUserId(ctx);
  if (id) return ctx.prisma.$exists.customer({ id, deleted: false });

  return false;
});

export const isOwnerOfOrder = rule()(async (_parent, args, ctx: Context) => {
  const id = getUserId(ctx);
  return ctx.prisma.$exists.order({
    id: args.id,
    deleted: false,
    customer: {
      id,
    },
  });
});

export const isAuthenticated = or(isCustomer, isManager);
