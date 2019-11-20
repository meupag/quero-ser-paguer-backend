import { Context } from '../../utils';
import { OrderSubscriptionPayloadSubscription } from '../../generated/prisma-client';

const newSubsOrder = (_parent, _args, ctx: Context): OrderSubscriptionPayloadSubscription => {
  // eslint-disable-next-line @typescript-eslint/camelcase
  return ctx.prisma.$subscribe.order({ mutation_in: 'CREATED' }).node();
};

const newOrder = {
  subscribe: newSubsOrder,
  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  resolve: payload => {
    return payload;
  },
};

export default {
  newOrder,
};
