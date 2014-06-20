package ua.banan.data.model.presentation;

public class BasicPresentation
{
	private String value;
	private String label;
	
	public BasicPresentation(String value, String label)
	{
		super();
		this.value = value;
		this.label = label;
	}

	public BasicPresentation() { }

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	
}
