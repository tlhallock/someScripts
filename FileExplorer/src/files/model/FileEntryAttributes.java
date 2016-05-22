package files.model;

import java.awt.Dimension;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.io.FileUtils;


public class FileEntryAttributes {


	public static enum FileEntryAttributeKey
	{
		All_Name("Name", StringValue.class),
		All_ConanicalPath("Path", StringValue.class),
		All_Filesystem("Filesystem", StringValue.class),
		
		File_Length("Size", SizeOnDisk.class),
		File_LastModified("Modified", DateValue.class),
		File_LastAccessed("Accessed", DateValue.class),
		File_Extension("Extension", StringValue.class),
		File_Permissions("Permisions", String.class), // was supposed to be permissions...
		File_Owner("Owner", StringValue.class),
		File_Group("Group", StringValue.class),
		File_CRC("Checksum", StringValue.class),
		File_Type("Type", StringValue.class),
		File_Created("Created", DateValue.class),
		
		Directory_NumSubfolders("# Contents", NumberValue.class),
		
		
		Audio_Artist("Artist", StringValue.class),
		Audio_Date("Release Date", DateValue.class),
		Audio_TrackNumber("Track", NumberValue.class),
		Audio_Disk("Disk", NumberValue.class),
		Audio_Tags("Tags", StringValue.class),
		Audio_Duration("Duration", DurationValue.class),
		
		Image_Size("Dimensions", DimensionValue.class),
		Image_Date("Date", DateValue.class),
		
		Link_Distination("Desination", StringValue.class),
		
		;
		
		Class c;
		String display;
		
		FileEntryAttributeKey(String displayName, Class type)
		{
			this.display = displayName;
			this.c = type;
		}
		
		public String getDisplay()
		{
			return display;
		}
	}
	
	public interface FileEntryAttributeValue extends Comparable<FileEntryAttributeValue>
	{
		public String display();
		
	}
	
	public static class StringValue implements FileEntryAttributeValue
	{
		final String value;
		
		StringValue(String value)
		{
			this.value = value;
		}

		@Override
		public String display() {
			return value;
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof StringValue))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			StringValue other = (StringValue) o;
			return value.compareTo(other.value);}
	}
	
	public static class SizeOnDisk implements FileEntryAttributeValue
	{
//		final BigInteger bytes;
//		
//		SizeOnDisk(BigInteger bytes)
//		{
//			this.bytes = bytes;
//		}

		final long bytes;
		
		SizeOnDisk(long bytes)
		{
			this.bytes = bytes;
		}

		@Override
		public String display()
		{
			return FileUtils.byteCountToDisplaySize(bytes);
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof SizeOnDisk))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			SizeOnDisk other = (SizeOnDisk) o;
			return Long.compare(bytes, other.bytes);
		}
	}
	public static class DateValue implements FileEntryAttributeValue
	{
		final long time;
		
		DateValue(long time)
		{
			this.time = time;
		}

		@Override
		public String display()
		{
			// Todo:
			return new Date(time).toString();
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof DateValue))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			DateValue other = (DateValue) o;
			return Long.compare(time, other.time);
		}
	}
	public static class NumberValue implements FileEntryAttributeValue
	{
		final BigInteger value;
		
		NumberValue(BigInteger time)
		{
			this.value = time;
		}

		@Override
		public String display()
		{
			return value.toString();
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof NumberValue))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			NumberValue other = (NumberValue) o;
			return value.compareTo(other.value);
		}
	}
	public static class DimensionValue implements FileEntryAttributeValue
	{
		final Dimension value;
		
		DimensionValue(Dimension time)
		{
			this.value = time;
		}

		@Override
		public String display()
		{
			return "[" + value.width + "," + value.height + "]";
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof DurationValue))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			DimensionValue other = (DimensionValue) o;
			
			long myArea = value.width * value.height;
			long otherArea = other.value.width * other.value.height;
			
			if (myArea == otherArea)
				return Integer.compare(value.width, other.value.width);
			else
				return Long.compare(myArea, otherArea);
		}
	}
		
	public static class DurationValue implements FileEntryAttributeValue
	{
		final BigInteger seconds;
		
		DurationValue(BigInteger time)
		{
			this.seconds = time;
		}

		@Override
		public String display()
		{
			return seconds.toString() + "s";
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof DurationValue))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			DurationValue other = (DurationValue) o;
			return seconds.compareTo(other.seconds);
		}
	}
	public static class Permissions implements FileEntryAttributeValue
	{
		final long flags;
		
		Permissions(long flags)
		{
			this.flags = flags;
		}

		@Override
		public String display()
		{
			return "da permissions" + flags;
		}

		@Override
		public int compareTo(FileEntryAttributeValue o) {
			if (o == null) return -1;
			if (!(o instanceof Permissions))
				return Integer.compare(findIndex(getClass()), findIndex(o.getClass()));
			Permissions other = (Permissions) o;
			return Long.compare(flags, other.flags);
		}
	}
	
	

	private static int findIndex(Class c)
	{
		for (int i=0;i<ALL_VALUES.length;i++)
			if (c.equals(ALL_VALUES[i]))
				return i;
		return ALL_VALUES.length + 1;
	}
	private static Class[] ALL_VALUES = 
	{
		StringValue.class,
		SizeOnDisk.class,
		DateValue.class,
		NumberValue.class,
		DimensionValue.class,
		DurationValue.class,
		Permissions.class,
	};
}
