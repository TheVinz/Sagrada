package ToolCards;

public interface IAbility{
	int getNumberOfParameters();
	void setParameter(Object o);
	void doAbility() throws MossaNonValidaException;
}