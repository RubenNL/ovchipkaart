package nl.rubend.ovchipkaart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {
	List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
	List<OVChipkaart> findByReizigerId(int id) throws SQLException;
	boolean save(OVChipkaart ovChipkaart);
	boolean update(OVChipkaart ovChipkaart);
	boolean delete(OVChipkaart ovChipkaart);
	List<OVChipkaart> findAll() throws SQLException;
}
