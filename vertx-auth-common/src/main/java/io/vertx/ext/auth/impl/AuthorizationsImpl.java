package io.vertx.ext.auth.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.vertx.ext.auth.Authorization;
import io.vertx.ext.auth.Authorizations;

public class AuthorizationsImpl implements Authorizations {

  private Map<String, Set<Authorization>> authorizations;

  public AuthorizationsImpl() {
    this.authorizations = new HashMap<>();
  }

  @Override
  public Authorizations add(String providerId, Authorization authorization) {
    Objects.requireNonNull(providerId);
    Objects.requireNonNull(authorization);

    getOrCreateAuthorizations(providerId).add(authorization);
    return this;
  }

  @Override
  public Authorizations add(String providerId, Set<Authorization> authorizations) {
    Objects.requireNonNull(providerId);
    Objects.requireNonNull(authorizations);

    getOrCreateAuthorizations(providerId).addAll(authorizations);
    return this;
  }

  @Override
  public Authorizations delete(String providerId) {
    Objects.requireNonNull(providerId);

    authorizations.remove(providerId);
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof AuthorizationsImpl))
      return false;
    AuthorizationsImpl other = (AuthorizationsImpl) obj;
    if (authorizations == null) {
      if (other.authorizations != null)
        return false;
    } else if (!authorizations.equals(other.authorizations))
      return false;
    return true;
  }

  @Override
  public Set<Authorization> get(String providerId) {
    Objects.requireNonNull(providerId);

    return authorizations.get(providerId);
  }

  private Set<Authorization> getOrCreateAuthorizations(String providerId) {
    Set<Authorization> result = authorizations.get(providerId);
    if (result == null) {
      result = new HashSet<>();
      authorizations.put(providerId, result);
    }
    return result;
  }

  @Override
  public Set<String> getProviderIds() {
    return authorizations.keySet();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((authorizations == null) ? 0 : authorizations.hashCode());
    return result;
  }

}
