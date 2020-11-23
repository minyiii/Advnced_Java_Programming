import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JFileChooserDemo extends JFrame{
	private final JTextArea outputArea;
	
	public JFileChooserDemo() throws IOException{	//constructor
		super("JFileChooser Demo");
		outputArea = new JTextArea();
		add(new JScrollPane(outputArea));	//outputArea is scrollable
		analyzePath();
	}
	
	public void analyzePath() throws IOException{
		Path path = getFileOrDirectoryPath();
		if(path!=null && Files.exists(path)){	//�Y���D�B�s�b(path�Y��null��ܨS�D)
			StringBuilder builder = new StringBuilder();
			//StringBuilder��append()�O���ѼƱ��b��StringBuilder�����(�[�����Pı)
			builder.append(String.format("%s:\n", path.getFileName()));
			builder.append(String.format("%s a directory\n", Files.isDirectory(path)?"Is":"Is not"));
			builder.append(String.format("%s an absolute path\n",path.isAbsolute()?"Is":"Is not"));
			builder.append(String.format("Last modified: %s\n", Files.getLastModifiedTime(path)));
			builder.append(String.format("Size: %s\n", Files.size(path)));
			builder.append(String.format("Path: %s\n", path));
			builder.append(String.format("Absolute path: %s\n",path.toAbsolutePath()));
			
			if(Files.isDirectory(path)){	//�Y���ؿ�
				builder.append(String.format("\nDirectory contents:\n"));
				
				DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
				for(Path p : directoryStream){
					builder.append(String.format("%s\n",p));
				}
			}
			
			outputArea.setText(builder.toString());
		}
		else{	//�Ypath���s�b
			JOptionPane.showMessageDialog(this, path.getFileName()+" does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private Path getFileOrDirectoryPath(){	//�|�^�ǩҿ蠟���|
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);	//�ɮ�,�ؿ�����Ū
		
		int result = fileChooser.showOpenDialog(this);
		
		if(result==JFileChooser.CANCEL_OPTION)
			System.exit(1);
		return fileChooser.getSelectedFile().toPath();
	}
}
