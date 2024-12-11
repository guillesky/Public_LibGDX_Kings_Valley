package msx_map_extractor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana extends JFrame implements ActionListener
{
	private int tam = 10;
	private int offset = 1856;
	private byte[] bytes;
	private HashMap<Byte, Image> hashImage = new HashMap<Byte, Image>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelPrincipal;
	private JPanel panelEste;
	private JTextField textFieldTam;
	private JPanel panel;
	private Image[][] tiles;
	private JButton btnNewButton;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */

	public class PanelLevel extends JPanel implements MouseListener
	{
		public PanelLevel()
		{
			super();
			this.addMouseListener(this);
		}

		@Override
		public void paint(Graphics g)
		{
			int x = 0;
			int y = 0;
			int count = 0;
			super.paint(g);
			for (int i = offset - 736; i < offset; i++)
			{
				x = count * tam;
				Image image = Ventana.this.hashImage.get(Ventana.this.bytes[i]);
				if (image == null)
					image = Ventana.this.tiles[12][0];

				g.drawImage(image, x, y, null);

				count++;
				if (count == 32)
				{
					count = 0;
					y += tam;
				}

			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			double x = e.getPoint().getX();
			double y = e.getPoint().getY();
			int ix = (int) (x / tam);
			int iY = (int) (y / tam);
			int index = iY * 32 + ix + offset - 736;
			byte b = bytes[index];
			System.out.println(b);

		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Create the frame.
	 */
	public Ventana(byte[] bytes)
	{
		this.bytes = bytes;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		this.panelPrincipal = new PanelLevel();
		this.contentPane.add(this.panelPrincipal, BorderLayout.CENTER);

		this.panelEste = new JPanel();
		this.contentPane.add(this.panelEste, BorderLayout.EAST);
		this.panelEste.setLayout(new GridLayout(0, 1, 0, 0));

		this.panel = new JPanel();
		this.panelEste.add(this.panel);

		this.textFieldTam = new JTextField();
		this.textFieldTam.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Offset",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.panel.add(this.textFieldTam);
		this.textFieldTam.setColumns(10);

		this.panel_1 = new JPanel();
		this.panelEste.add(this.panel_1);

		this.btnNewButton = new JButton("New button");
		this.btnNewButton.addActionListener(this);
		this.panel_1.add(this.btnNewButton);
		this.lalala();
		this.setColors();

		this.setVisible(true);
	}

	private void setColors()
	{
		this.hashImage.put((byte) 0x00, this.tiles[6][12]); // vacio
		this.hashImage.put((byte) 0x14, this.tiles[10][6]); // limite inferior
		this.hashImage.put((byte) 0x13, this.tiles[0][0]); // limite inferior
		this.hashImage.put((byte) 23, this.tiles[2][8]); // pie escalera pos 1
		this.hashImage.put((byte) 24, this.tiles[2][9]);// pie escalera pos 2
		this.hashImage.put((byte) 34, this.tiles[2][10]); // escalera pos 1
		this.hashImage.put((byte) 35, this.tiles[2][11]); // escalera pos 2

		this.hashImage.put((byte) 21, this.tiles[2][12]); // pie escalera neg 1
		this.hashImage.put((byte) 22, this.tiles[2][13]);// pie escalera neg 2
		this.hashImage.put((byte) 32, this.tiles[2][14]); // escalera neg 1
		this.hashImage.put((byte) 33, this.tiles[2][15]); // escalera neg 2

	}

	private void lalala()
	{
		try
		{
			// Carga la imagen completa (el tileset)
			BufferedImage tileset = ImageIO.read(new File("assets/tiles2.png"));

			// Tamaño de cada tile en píxeles
			int tileWidth = 10;
			int tileHeight = 10;

			// Número de tiles en filas y columnas
			int cols = tileset.getWidth() / tileWidth;
			int rows = tileset.getHeight() / tileHeight;

			// Array para almacenar los tiles individuales
			tiles = new Image[rows][cols];

			// Recorta cada tile individual
			for (int row = 0; row < rows; row++)
			{
				for (int col = 0; col < cols; col++)
				{
					tiles[row][col] = tileset.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
				}
			}

			// Ejemplo: puedes guardar o usar los tiles
			System.out.println("Se han recortado " + (rows * cols) + " tiles correctamente.");

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnNewButton)
		{
			do_btnNewButton_actionPerformed(e);
		}
	}

	protected void do_btnNewButton_actionPerformed(ActionEvent e)
	{
		int offset=Integer.parseInt(this.textFieldTam.getText());
		this.offset=offset;
		this.repaint();
	}
}
