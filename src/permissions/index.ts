import { shield, and } from 'graphql-shield';
import * as rules from './rules';

export const permissions = shield({
  Query: {
    customers: and(rules.isAuthenticated, rules.isManager),
    orders: rules.isAuthenticated,
    order: and(rules.isAuthenticated, rules.isOwnerOfOrder),
  },
  Mutation: {
    createProduct: and(rules.isAuthenticated, rules.isManager),
    updateProduct: and(rules.isAuthenticated, rules.isManager),
    deleteProduct: and(rules.isAuthenticated, rules.isManager),
    createOrder: rules.isAuthenticated,
    cancelOrder: and(rules.isAuthenticated, rules.isOwnerOfOrder),
    //createManager: and(rules.isAuthenticated, rules.isManager),
  },
  Product: {
    store: and(rules.isAuthenticated, rules.isManager),
  },
});
