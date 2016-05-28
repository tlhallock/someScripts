package files.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;

import files.app.Application;
import files.app.ExtensionManager;
import files.model.FileEntryAttributes.DateValue;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;
import files.model.FileEntryAttributes.SizeOnDisk;
import files.model.FileEntryAttributes.StringValue;

public class FileEntry
{
	private Path path;
	
	private HashMap<FileEntryAttributeKey, FileEntryAttributeValue> values = new HashMap<>();
	
	private boolean hidden;

	public FileEntry(Path path)
	{
		this.path = path;
	}

        public String toString()
        {
            return path.toString();
        }

	public String getExtension()
	{
		StringValue val = (StringValue) values.get(FileEntryAttributeKey.File_Extension);
		return val == null ? null : val.display();
	}
	
	public String getName() {
		return ((StringValue) values.get(FileEntryAttributeKey.All_Name)).display();
	}
	
	public boolean isHidden()
	{
		return hidden;
	}

        public boolean isParentOf(FileEntry other)
        {
            return getPath().equals(other.getPath().getParent());
        }
        
	public Path getPath() {
		return path;
	}

	public FileEntryAttributeValue get(FileEntryAttributeKey key) {
		return values.get(key);
	}
	
	private static String getExtension(String filename)
	{
		int lastIndexOf = filename.lastIndexOf('.');
		if (lastIndexOf < 0)
			return null;
		return filename.substring(lastIndexOf + 1);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof FileEntry))
			return false;
		FileEntry entry = (FileEntry) other;
		return entry.getPath().equals(getPath());
	}
	
	public static FileEntry load(Path path) throws IOException
	{
		// Use file type detector...
		
		
		File file = path.toFile();
		BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
		DosFileAttributes attrsW = Files.getFileAttributeView(path, DosFileAttributeView.class).readAttributes();
		PosixFileAttributes attrsP = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();

		
		Path filenamePath = path.getFileName();
		String filename = filenamePath == null ? "root" : filenamePath.toString();
		String ext = getExtension(filename);
		String type = Application.getApplication().getKnownFileTypes().getDisplay(ext);
		
		FileEntry entry = new FileEntry(path.toRealPath());
		
		entry.hidden = Files.isHidden(entry.path);
		
		entry.values.put(FileEntryAttributeKey.All_Name,    		new StringValue(filename));
		entry.values.put(FileEntryAttributeKey.All_ConanicalPath,	new StringValue(path.toRealPath().toString()));
		entry.values.put(FileEntryAttributeKey.All_Filesystem,		new StringValue(path.getFileSystem().toString()));
		
		entry.values.put(FileEntryAttributeKey.File_Length, 		new SizeOnDisk (attrs.size()));
		entry.values.put(FileEntryAttributeKey.File_LastModified, 	new DateValue(attrs.lastModifiedTime().toMillis()));
		entry.values.put(FileEntryAttributeKey.File_LastAccessed, 	new DateValue(attrs.lastAccessTime().toMillis()));
		entry.values.put(FileEntryAttributeKey.File_Created,	 	new DateValue(attrs.creationTime().toMillis()));

		entry.values.put(FileEntryAttributeKey.File_Owner,	 		new StringValue(attrsP.owner().getName()));
		entry.values.put(FileEntryAttributeKey.File_Group,	 		new StringValue(attrsP.group().getName()));
		entry.values.put(FileEntryAttributeKey.File_Permissions,	new StringValue(PosixFilePermissions.toString(attrsP.permissions())));

		if (ext != null)
		entry.values.put(FileEntryAttributeKey.File_Extension,		new StringValue(ext));

		if (Files.isDirectory(path))
		entry.values.put(FileEntryAttributeKey.File_Type,			new StringValue(ExtensionManager.DIRECTORY_DISPLAY_TYPE));
		else if (type != null)
		entry.values.put(FileEntryAttributeKey.File_Type,			new StringValue(ext));
		else
		entry.values.put(FileEntryAttributeKey.File_Type,			new StringValue(ExtensionManager.UNKNOWN_DISPLAY_TYPE));
			

//		entry.values.put(FileEntryAttributeKey.File_Key,		 	new DateValue(attrs.fileKey()));		
//		File_CRC("Checksum", StringValue.class),
//		Directory_NumSubfolders("# Contents", NumberValue.class),
//		
//		Audio_Artist("Artist", StringValue.class),
//		Audio_Date("Release Date", DateValue.class),
//		Audio_TrackNumber("Track", NumberValue.class),
//		Audio_Disk("Disk", NumberValue.class),
//		Audio_Tags("Tags", StringValue.class),
//		Audio_Duration("Duration", DurationValue.class),
//		
//		Image_Size("Dimensions", DimensionValue.class),
//		Image_Date("Date", DateValue.class),
//		
//		Link_Distination("Desination", StringValue.class),
		
		return entry;
	}
	
	public static String getParentPath(Path p)
	{
		Path parent = p.getParent();
		if (parent == null)
			return "/";
		else
			return parent.toString();
	}
}
