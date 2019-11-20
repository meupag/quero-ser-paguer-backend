import { Context } from '../../utils';
import { OrderSubscriptionPayloadSubscription } from '../../generated/prisma-client';

const newSubsOrder = (_parent, _args, ctx: Context): OrderSubscriptionPayloadSubscription => {
  return ctx.prisma.$subscribe.order({ mutation_in: 'CREATED' }).node();
};

const newOrder = {
  subscribe: newSubsOrder,
  resolve: payload => {
    return payload;
  },
};

export default {
  newOrder,
};
