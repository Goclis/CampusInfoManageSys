/**
 * 
 */
package vclient.view.library.reader;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vclient.srv.ClientSrvHelper;

import common.vo.library.Reader;

/**
 * 显示读者个人信息的面板，有挂失读者证的功能
 * 
 * @author zhongfang
 * 
 */
public class CardInfoPanel extends JPanel implements ActionListener {

	// attributes
	private JLabel idLb1, nameLb1, certificateLb1, effectiveLb1, expireLb1,
			moneyLb1, statusLb1, ruleLb1;
	private Vector info;
	private JButton lossBt;
	private JPanel lossP, upP;
	private Reader reader;
	private ReaderLibraryFrame parentF;
	// private ClientSrvReaderHelper readerClient = new ClientSrvReaderHelper();

	private ImageIcon background = new ImageIcon("1.jpg");
	private Image im = Toolkit.getDefaultToolkit().getImage("1.jpg");

	public CardInfoPanel(ReaderLibraryFrame parentF) {
		this.parentF = parentF;
		this.reader = parentF.getReader();
		this.setLayout(new BorderLayout());

		lossP = new JPanel();
		lossBt = new JButton("挂失读者证");
		lossP.add(lossBt);
		this.add(lossP, BorderLayout.SOUTH);

		idLb1 = new JLabel("编号:" + reader.getReaderId());
		nameLb1 = new JLabel("学号:" + reader.getUserId());
		certificateLb1 = new JLabel("注册日期:" + reader.getCertificateTime());
		effectiveLb1 = new JLabel("生效日期:" + reader.getEffectiveTime());
		expireLb1 = new JLabel("失效日期:" + reader.getExpireTime());
		moneyLb1 = new JLabel("余额:" + reader.getMoney());
		statusLb1 = new JLabel("状态:" + reader.getStatus());
		ruleLb1 = new JLabel("规则：" + reader.getRule().getRuleId() + "."
				+ reader.getRule().getRuleName());

		upP = new JPanel(new GridLayout(4, 2));

		upP.add(idLb1);
		upP.add(nameLb1);
		upP.add(certificateLb1);
		upP.add(effectiveLb1);
		upP.add(expireLb1);
		upP.add(moneyLb1);
		upP.add(statusLb1);
		upP.add(ruleLb1);
		this.add(upP);

		lossBt.addActionListener(this);

	}

	// public void paintComponent(Graphics g){
	// int h,w;
	// g.drawImage(im, 0, 0, null);
	// }
	public Vector readerInfo() {
		Vector info = new Vector();

		return info;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == lossBt)
			reportLoss();
	}

	private void reportLoss() {
		int res = JOptionPane.showConfirmDialog(this,
				"挂失后图书证将无法使用！ 挂失后自己不能解除，需要工作人员处理！", "确认挂失读者证",
				JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			if (reader.getStatus().equals("已挂失")) {
				JOptionPane.showMessageDialog(this, "你的读者证已挂失\n不能再次挂失");
			} else {
				reader = ClientSrvHelper.reportLossReader(reader);
				if (reader == null)
					JOptionPane.showMessageDialog(this, "挂失失败");
				else
					JOptionPane.showMessageDialog(this, "挂失成功");
			}
		}
	}
}
