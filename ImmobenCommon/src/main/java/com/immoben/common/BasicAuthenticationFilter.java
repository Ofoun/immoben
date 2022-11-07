package com.immoben.common;
//package com.immoben.common;
//
//import java.util.List;
//
//import javax.annotation.Priority;
//import javax.persistence.EntityManager;
//import com.immoben.common.entity.Customer;
//import com.immoben.common.util.RestJpaLifecycleProvider;
//
//
///**
// * JAX-RS filter provider that performs HTTP "basic" authentication on any REST service request. This aspect-oriented
// * design swaps "Authorization" headers for "Requester-Identity" during authentication.
// */
//@Provider
//@Priority(AUTHENTICATION)
//public class BasicAuthenticationFilter implements ContainerRequestFilter {
//	static private final String QUERY_PERSON = "select a from Account as a where a.email = :email";
//
//	/**
//	 * HTTP request header for the authenticated requester's identity.
//	 */
//	static public final String REQUESTER_IDENTITY = "Requester-Identity";
//
//
//	/**
//	 * Performs HTTP "basic" authentication by calculating a password hash from the password contained in the request's
//	 * "Authorization" header, and comparing it to the one stored in the person matching said header's username. The
//	 * "Authorization" header is consumed in any case, and upon success replaced by a new "Requester-Identity" header that
//	 * contains the authenticated person's identity. The filter chain is aborted in case of a problem.
//	 * @param requestContext {@inheritDoc}
//	 * @throws NullPointerException if the given argument is {@code null}
//	 * @throws ClientErrorException (400) if the "Authorization" header is malformed, or if there is a pre-existing
//	 *         "Requester-Identity" header
//	 */
//	public void filter (final ContainerRequestContext requestContext) throws NullPointerException, ClientErrorException {
//		if (requestContext.getHeaders().containsKey(REQUESTER_IDENTITY)) throw new ClientErrorException(BAD_REQUEST);
//		if ("POST".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("accounts")) return;
////		if ("GET".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("accounts")) return;
////		if ("POST".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("rentArticles")) return;
//		if ("GET".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("rentArticles")) return;
////		if ("POST".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("saleArticles")) return;
//		if ("GET".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("saleArticles")) return;
////		if ("POST".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("documents/")) return;
//		if ("GET".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("documents/")) return;
////		if ("POST".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("messages")) return;
//		if ("GET".equals(requestContext.getMethod()) & requestContext.getUriInfo().getPath().startsWith("messages")) return;
//
//		final List<String> header = requestContext.getHeaders().remove(AUTHORIZATION);
//		final String textCredentials = header == null || header.isEmpty() ? null : header.get(0);
//
//		if (textCredentials != null) {
//			final HttpCredentials.Basic credentials = RestCredentials.newBasicInstance(textCredentials);
//
//			final EntityManager entityManager = RestJpaLifecycleProvider.entityManager("heroku_771eaf6d32adcb8");
//			final Customer requester = entityManager
//				.createQuery(QUERY_PERSON, Customer.class)
//				.setParameter("email", credentials.getUsername())
//				.getResultList()
//				.stream()
//				.findFirst()
//				.orElse(null);
//
//			if (requester != null) {
//				final String leftHash = requester.getPasswordHash();
//				final String rightHash = HashCodes.sha2HashText(256, credentials.getPassword());
//
//				if (leftHash.equals(rightHash)) {
//					requestContext.getHeaders().putSingle(REQUESTER_IDENTITY, Long.toString(requester.getId()));
//					return;
//				}
//			}
//		}
//
//		requestContext.abortWith(Response.status(UNAUTHORIZED).header(WWW_AUTHENTICATE, "Basic").build());
//	}
//}