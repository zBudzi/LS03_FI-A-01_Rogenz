package model.persistenceDummy;

import model.persistence.IAttemptPersistance;
import model.persistence.ILevelPersistance;
import model.persistence.IPersistance;
import model.persistence.ITargetPersistance;
import model.persistence.IUserPersistance;

public class PersistanceDummy implements IPersistance{

	private LevelDummy levelDummy;
	private UserDummy userDummy;
	private AttemptDummy attemptDummy;
	private TargetDummy targetDummy;

	public PersistanceDummy() {

		this.levelDummy = new LevelDummy();
		this.userDummy = new UserDummy();
		this.attemptDummy = new AttemptDummy();
		this.targetDummy = new TargetDummy();
	}

	@Override
	public IAttemptPersistance getAttemptPersistance() {
		return this.attemptDummy;
	}

	@Override
	public ILevelPersistance getLevelPersistance() {
		return this.levelDummy;
	}

	@Override
	public ITargetPersistance getTargetPersistance() {
		return this.targetDummy;
	}

	@Override
	public IUserPersistance getUserPersistance() {
		return this.userDummy;
	}


}
