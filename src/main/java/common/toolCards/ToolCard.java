package common.toolCards.;

public interface ToolCard {
	Object nextParameter();
	void setParameter(Object o);
	void doAbility() throws MossaNonValidaException;
}