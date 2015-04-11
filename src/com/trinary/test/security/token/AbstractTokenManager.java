package com.trinary.test.security.token;

public abstract class AbstractTokenManager implements TokenManager {
	protected TokenFactory tokenFactory;
	
	AbstractTokenManager() {
		this.tokenFactory = getTokenFactory();
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.security.token.TokenManager#getTokenFactory()
	 */
	@Override
	public TokenFactory getTokenFactory() {
		// TODO Auto-generated method stub
		return tokenFactory;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.security.token.TokenManager#setTokenFactory(com.trinary.test.security.token.TokenFactory)
	 */
	@Override
	public void setTokenFactory(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}
}