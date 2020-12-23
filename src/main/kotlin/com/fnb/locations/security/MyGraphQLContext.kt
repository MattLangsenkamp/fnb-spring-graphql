package com.fnb.locations.security

import com.expediagroup.graphql.execution.GraphQLContext
import com.fnb.locations.model.LoggedInUser

class MyGraphQLContext(val loggedInUser: LoggedInUser?) : GraphQLContext
